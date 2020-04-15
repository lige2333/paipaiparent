package cn.lige2333.paipai.query;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ProductQueryObject {
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private String keyword;
    private Integer state;
    private Integer sellerId;
    private String district;
    private String category;
    private Set<Object> keys;
    public int getStart() {
        return (currentPage - 1) * pageSize;
    }

    @Override
    public String toString() {
        return "ProductQueryObject{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                getStart() +
                '}';
    }
}
