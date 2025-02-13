package com.wannago.entity;

import java.io.Serializable;

public class LocationSiId implements Serializable {
    private Integer lsIdx;
    private Integer ldIdx;

    public LocationSiId() {
    }

    public LocationSiId(Integer lsIdx, Integer ldIdx) { 
        this.lsIdx = lsIdx;
        this.ldIdx = ldIdx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationSiId that = (LocationSiId) o;
        return lsIdx.equals(that.lsIdx) && ldIdx.equals(that.ldIdx);
    }
}
