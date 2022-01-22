package controller;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class NetworkProvider {
    private static NetworkProvider instance;


    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String ERROR_MESSAGE = "Ошибка при подключении к серверу";

    private NetworkProvider() {

    }

    public static NetworkProvider getInstance() {
        log.info("Was create network provider");
        if (instance == null) {
            instance = new NetworkProvider();
        }
        return instance;
    }


    public String getAllWorkplace() {
        log.info("was get all workplaces");
        URL url;
        try {
            url = new URL("http://192.168.1.64:8099/v1/test/findAllWorkplace");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
            return null;
        }
    }

    public String getAllPeripheryType() {
        log.info("was get all periphery type");
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/getPeripheryType");
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
            return null;
        }

    }

    public String getComputerToWorkplaceId(Long workplaceId) {
        log.info("was get computer with workplace id {}", workplaceId);
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/findComputerByWorkPlaceId=");
            builder.append(workplaceId);
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
            return null;
        }
    }

    public String getPeripheryToWorkplaceId(Long workplaceId) {
        log.info("was get periphery with workplace id {}", workplaceId);
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/getPeriphery=");
            builder.append(workplaceId);
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
            return null;
        }

    }

    public String createNewWorkplace(String string) {
        log.info("was create new workplace");
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/createNewWorkplace");
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);

            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(string);


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
            return null;
        }

    }
    public void createNewComputer(String string) {
        log.info("was create new computer");
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/setComputer");
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(string);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("request 'create new computer ' returned next: {}", content);
            in.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
        }

    }

    public void createNewPeriphery(String string) throws IOException {
        log.info("was create new periphery");
        if (string == null) {
            return;
        }
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/setPeriphery");
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);

            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(string);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("request 'create new periphery ' returned next: {}", content);
            in.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
        }

    }

    public void delComputerToWorkplaceId(Long computerId) {
        log.info("was del computer with workplace id {}", computerId);
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/deleteComputerById=");
            builder.append(computerId);
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("request 'del computer' returned next: {}", content);

            in.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
        }
    }
    public void delPeripheryToId(Long id) {
        log.info("was del periphery with id {}", id);
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/delPeriphery=");
            builder.append(id);
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("request 'del periphery' returned next: {}", content);
            in.close();


        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
        }
    }
    public void delWorkplaceToId(Long workplaceId) {
        log.info("was del workplace with id {}", workplaceId);
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/deleteById=");
            builder.append(workplaceId);
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("request 'del workplace' returned next: {}", content);
            in.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
        }
    }

    public void delPeripheryByWorkplaceId(Long workplaceId) {
        log.info("was del periphery with workplace id {}", workplaceId);
        URL url;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("http://192.168.1.64:8099/v1/test/delPeripheryByWorkplaceId=");
            builder.append(workplaceId);
            url = new URL(builder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            log.info("request 'del periphery ' returned next: {}", content);
            in.close();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
        }
    }
}
