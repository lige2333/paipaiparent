package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lige2333.paipai.entity.Address;
import cn.lige2333.paipai.dao.AddressMapper;

@Transactional
@Service
public class AddressService {
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private OrderMapper orderMapper;

	public AddressMapper getAddressMapper() {
		return addressMapper;
	}

	public void updateAddress(Address address) {
		addressMapper.insert(address);
		orderMapper.updateOrderState(4,address.getId(),address.getOrderId());
	}
}