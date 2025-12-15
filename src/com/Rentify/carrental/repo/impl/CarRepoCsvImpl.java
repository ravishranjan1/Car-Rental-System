package com.Rentify.carrental.repo.impl;

import com.Rentify.carrental.exceptions.CarAlreadyPresentException;
import com.Rentify.carrental.exceptions.CarNotFoundException;
import com.Rentify.carrental.model.CarModel;
import com.Rentify.carrental.repo.CarRepo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarRepoCsvImpl implements CarRepo {
    private final Path csvpath ;
    private final String HEADER = "id,make,model,category,seats,dailyRate,available";

    public CarRepoCsvImpl(Path csvpath) {
        this.csvpath = csvpath;
    }

    @Override
    public void addCar(CarModel c) throws IOException, CarAlreadyPresentException {
        List<CarModel> list = findAll();
        for(CarModel x : list){
            if(x.getId().equalsIgnoreCase(c.getId())){
                throw new CarAlreadyPresentException("Car with this Id : "+c.getId()+" is  already present.");
            }
        }
        list.add(c);
        ensureHeaderInFile();
        writeAll(list);

    }
    @Override
    public void updateCar(CarModel c) throws IOException,CarNotFoundException{
        boolean isFound = false;
        List<CarModel> list = findAll();
        for(int i=0; i<list.size(); i++){
            CarModel x = (CarModel) list.get(i);
            if(x.getId().equalsIgnoreCase((c.getId()))){
                isFound = true;
                x.setMake(c.getMake());
                x.setModel(c.getModel());
                x.setCategory(c.getCategory());
                x.setSeats(c.getSeats());
                x.setDailyRate(c.getDailyRate());
                x.setAvailable(c.isAvailable());
                list.set(i,x);
            }
        }
        if(!isFound){
            throw new CarNotFoundException("Car With this id"+c.getId()+" is not present");
        }

        writeAll(list);
    }

    @Override
    public void removeCar(String id) throws IOException, CarNotFoundException {
        boolean isFound = false;
        List<CarModel> list = findAll();
        Iterator<CarModel> iterator = list.iterator();
        while (iterator.hasNext()) {
            CarModel c = iterator.next();
            if (c.getId().equalsIgnoreCase(id)) {
                iterator.remove();
                isFound = true;
                break;
            }
        }
        if(!isFound){
            throw new CarNotFoundException("Car with this Id : "+id+" not found");
        }

        writeAll(list);
    }


    private void writeAll(List<CarModel> list) throws IOException{
        BufferedWriter bw = Files.newBufferedWriter(csvpath);
        ensureHeaderInFile();
        bw.write(HEADER);
        bw.newLine();
        String line ;
        for(CarModel c : list){
            line = c.getId()+","+
                    c.getMake()+","+
                    c.getModel()+","+
                    c.getCategory()+","+
                    c.getSeats()+","+
                    c.getDailyRate()+","+
                    c.isAvailable();
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    @Override
    public List<CarModel> findAll() throws IOException{
        List<CarModel> list = new ArrayList<CarModel>();
        BufferedReader br = Files.newBufferedReader(csvpath);
        String line = br.readLine();
        while((line=br.readLine()) != null){
            if(!line.trim().isEmpty()){
                CarModel c = parseIntoCarModel(line);
                list.add(c);
            }
        }
        return list;
    }

    private CarModel parseIntoCarModel(String line){
        String[] word = line.split(",");
        CarModel c = new CarModel();
        c.setId(word[0]);
        c.setMake(word[1]);
        c.setModel(word[2]);
        c.setCategory(word[3]);
        c.setSeats(Integer.parseInt(word[4]));
        c.setDailyRate(Double.parseDouble(word[5]));
        c.setAvailable(Boolean.parseBoolean(word[6]));

        return c;
    }

    private void ensureHeaderInFile() throws IOException{
        if(Files.notExists(csvpath)){
            Files.createDirectories(csvpath.getParent());
            BufferedWriter bw = Files.newBufferedWriter(csvpath);
            bw.write(HEADER);
            bw.newLine();
            bw.close();
        }
    }

}
