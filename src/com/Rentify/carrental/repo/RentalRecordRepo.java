package com.Rentify.carrental.repo;

import com.Rentify.carrental.exceptions.RentalException;
import com.Rentify.carrental.model.RentalRecordModel;

import java.io.IOException;
import java.util.List;

public interface RentalRecordRepo {
    void saveRecord(RentalRecordModel r)throws IOException;
    void updateRecord(RentalRecordModel r) throws IOException, RentalException;
    List<RentalRecordModel> findAll() throws IOException;
}
