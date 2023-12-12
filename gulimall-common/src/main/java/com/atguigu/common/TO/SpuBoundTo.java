package com.atguigu.common.TO;

import java.util.Objects;
import lombok.Data;

import java.math.BigDecimal;


public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;

    public SpuBoundTo(Long spuId, BigDecimal buyBounds, BigDecimal growBounds) {
        this.spuId = spuId;
        this.buyBounds = buyBounds;
        this.growBounds = growBounds;
    }

    public SpuBoundTo() {
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public BigDecimal getBuyBounds() {
        return buyBounds;
    }

    public void setBuyBounds(BigDecimal buyBounds) {
        this.buyBounds = buyBounds;
    }

    public BigDecimal getGrowBounds() {
        return growBounds;
    }

    public void setGrowBounds(BigDecimal growBounds) {
        this.growBounds = growBounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpuBoundTo that = (SpuBoundTo) o;
        return Objects.equals(spuId, that.spuId) && Objects.equals(buyBounds, that.buyBounds) && Objects.equals(growBounds, that.growBounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spuId, buyBounds, growBounds);
    }

    @Override
    public String toString() {
        return "SpuBoundTo{" +
                "spuId=" + spuId +
                ", buyBounds=" + buyBounds +
                ", growBounds=" + growBounds +
                '}';
    }
}
