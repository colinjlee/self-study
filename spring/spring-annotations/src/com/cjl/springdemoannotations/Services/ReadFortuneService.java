package com.cjl.springdemoannotations.Services;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class ReadFortuneService implements FortuneService {

    private String filePath = new File("").getAbsolutePath()
            + File.separator + "src" + File.separator + "fortunes.txt";
    private Random random;
    private List<String> fortuneList;

    public ReadFortuneService() {
        random = new Random();
        fortuneList = new ArrayList<>();
    }

    @PostConstruct
    private void setFortuneList() {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Read in file does not exist");
            return;
        }

        try {
            fortuneList = Files.lines(file.toPath()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFortune() {
        int size = fortuneList.size();

        if (size == 0) {
            return "No fortune";
        } else {
            return fortuneList.get(random.nextInt(size));
        }
    }
}
