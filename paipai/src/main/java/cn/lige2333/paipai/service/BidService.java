package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.ProductMapper;
import cn.lige2333.paipai.entity.Order;
import cn.lige2333.paipai.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lige2333.paipai.entity.Bid;
import cn.lige2333.paipai.dao.BidMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class BidService {
	@Autowired
	private BidMapper bidMapper;
	@Autowired
	private ProductMapper productMapper;

	public BidMapper getBidMapper() {
		return bidMapper;
	}

    public void createBid(Bid bid) {
		synchronized (bid.getBidPrice().toString().trim().intern()){
			BigDecimal max = bidMapper.selectMaxBidPriceByProId(bid.getProduct().getId())==null?productMapper.selectStartPriceById(bid.getProduct().getId()):bidMapper.selectMaxBidPriceByProId(bid.getProduct().getId());
			BigDecimal minAddPrice = productMapper.selectMinAddPriceById(bid.getProduct().getId());
			if(max.add(minAddPrice).compareTo(bid.getBidPrice())>0){
				throw new RuntimeException("系统繁忙，请稍后再试");
			}
			bidMapper.insertBid(bid);
			productMapper.updateCurentPrice(bid.getBidPrice(),bid.getProduct().getId());
			try {
				WebSocketServer.BroadCastInfo("更新竞价！");
			} catch (IOException e) {
				throw new RuntimeException("系统繁忙，请稍后再试");
			}
		}
    }

	public List<Bid> getBidHistory(Integer id) {
		return bidMapper.getBidHistory(id);
	}


    public List<Bid> getBiddersByProId(Integer proId) {
		return bidMapper.selectByProId(proId);
    }
}