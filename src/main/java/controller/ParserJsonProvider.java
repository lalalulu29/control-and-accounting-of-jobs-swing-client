package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

import model.Computer;
import model.Periphery;
import model.PeripheryType;
import model.Workplace;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ParserJsonProvider {
    private static ParserJsonProvider instance;
    private static final Gson gson = new Gson();

    private static final String UTF8 = "UTF-8";


    private ParserJsonProvider() {
    }

    public static ParserJsonProvider getInstance() {
        log.info("Was create parser json provider");
        if (instance == null) {
            instance = new ParserJsonProvider();
        }
        return instance;
    }

    public Iterable<Workplace> parsWorkplaces(String string) {
        log.info("Was parse workplaces");

        Type itemsListType = new TypeToken<List<Workplace>>() {}.getType();

        return gson.fromJson(string, itemsListType);
    }

    public Workplace parsWorkplace(String string) {
        log.info("Was parse workplace");

        Type itemsListType = new TypeToken<Workplace>() {}.getType();
        return gson.fromJson(string, itemsListType);
    }


    public Iterable<Computer> parsComputers(String string) {
        log.info("Was parse computers");

        Type itemsListType = new TypeToken<List<Computer>>() {}.getType();

        return gson.fromJson(string, itemsListType);
    }

    public Iterable<Periphery> parsPeriphery(String string) {
        log.info("Was parse periphery");

        Type itemsListType = new TypeToken<List<Periphery>>() {}.getType();

        return gson.fromJson(string, itemsListType);
    }

    public Iterable<PeripheryType> parsAllPeripheryType(String string) {
        log.info("Was parse all periphery type");

        Type itemsListType = new TypeToken<List<PeripheryType>>() {}.getType();

        return gson.fromJson(string, itemsListType);
    }

    public String parsWorkplaceToString(Workplace workplace) throws UnsupportedEncodingException {
        log.info("Was parse from model workplace to sting");

        if(Objects.equals(workplace.getMac(), "")) workplace.setMac(null); else workplace.setMac(URLEncoder.encode(workplace.getMac(), UTF8));
        if(Objects.equals(workplace.getIp(), "")) workplace.setIp(null); else workplace.setIp(URLEncoder.encode(workplace.getIp(), UTF8));

        workplace.setDomainName(URLEncoder.encode(workplace.getDomainName(), UTF8));

        return gson.toJson(workplace);
    }

    public String parsComputerToString(Computer computer) throws UnsupportedEncodingException {
        log.info("Was parse from model computer to sting");

        if(Objects.equals(computer.getVideoCard(), "")) computer.setVideoCard(null); else computer.setVideoCard(URLEncoder.encode(computer.getVideoCard(), UTF8));
        if(Objects.equals(computer.getCaseFormFactor(), "")) computer.setCaseFormFactor(null); else computer.setCaseFormFactor(URLEncoder.encode(computer.getCaseFormFactor(), UTF8));

        computer.setPowerSupply(URLEncoder.encode(computer.getPowerSupply(), UTF8));
        computer.setMotherboard(URLEncoder.encode(computer.getMotherboard(), UTF8));
        computer.setRam(URLEncoder.encode(computer.getRam(), UTF8));
        computer.setCpu(URLEncoder.encode(computer.getCpu(), UTF8));
        computer.setHddOrSsd(URLEncoder.encode(computer.getHddOrSsd(), UTF8));

        return gson.toJson(computer);
    }

    public String parsPeripheryToString(Periphery periphery) throws UnsupportedEncodingException {
        log.info("Was parse from model periphery to sting");

        if(Objects.equals(periphery.getModel(), "")) periphery.setModel(null); else periphery.setModel(URLEncoder.encode(periphery.getModel(), UTF8));
        if(Objects.equals(periphery.getComments(), "")) periphery.setComments(null); else periphery.setComments(URLEncoder.encode(periphery.getComments(), UTF8));
        if (periphery.getModel() == null && periphery.getComments() == null) return null;

        return gson.toJson(periphery);


    }


}
