package com.example.demo.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
        Path path=Path.of("import.txt");
        BufferedReader reader=new BufferedReader(new FileReader(String.valueOf(path)));
        String line;
        List<Entity> entityList=new ArrayList<>();
        while ((line=reader.readLine())!=null){
            Entity entity;
            String[] values=line.trim().split("[,:]");
            String businessRelation=values[1];
            switch (businessRelation.toLowerCase()){
                case "externalprivate":
                    entity=new ExternalPrivateEntity(values[3],values[5],values[7]);
                    entityList.add(entity);
                    break;
                case "externalbusiness":
                    entity=new ExternalBusinessEntity(values[3],values[5],values[7],values[9]);
                    entityList.add(entity);
                    break;
                case "internal":
                    entity=new InternalEntity(values[3],values[5],values[7],InternalTeam.valueOf(values[9]),Boolean.parseBoolean(values[11]));
                    entityList.add(entity);
                    break;
            }
        }
        for (Entity e:entityList){
            String _class;
            _class=e.getClass().toString();
            String[] strings=_class.split("[ .]");
            System.out.println(e.getClass().toString().split("[ .]")[strings.length-1]);
//            System.out.println(e);
        }
    }
}
