package com.example.demo.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {


        String s="|businessRelation:externalprivate,name: EP1,phoneNumber:EPphoneN1,email:EPemail1|businessRelation:externalbusiness,name:EB1,phoneNumber:EBphoneN1,email:EBemail1,company:company1|businessRelation:internal,name:I1,phoneNumber:Iphone1,email:Iemail1,internalTeam:HR,isManager:true";
        String[] sArray=s.split("\\|");
        List<Entity> entityArrayList=new ArrayList<>();
        for (int i=1;i<sArray.length;i++){
            Entity entity1;
            String[] sArrayArray=sArray[i].split("[,:]");
            String businessRelation=sArrayArray[1];
            switch (businessRelation.toLowerCase()){
                case "externalprivate":
                    entity1=new ExternalPrivateEntity(sArrayArray[3],sArrayArray[5],sArrayArray[7]);
                    entityArrayList.add(entity1);
                    break;
                case "externalbusiness":
                    entity1=new ExternalBusinessEntity(sArrayArray[3],sArrayArray[5],sArrayArray[7],sArrayArray[9]);
                    entityArrayList.add(entity1);
                    break;
                case "internal":
                    entity1=new InternalEntity(sArrayArray[3],sArrayArray[5],sArrayArray[7],InternalTeam.valueOf(sArrayArray[9]),Boolean.parseBoolean(sArrayArray[11]));
                    entityArrayList.add(entity1);
                    break;
            }
        }
        System.out.println(entityArrayList);

//        Path path= Path.of("testimport.txt");
//        BufferedReader reader=new BufferedReader(new FileReader(String.valueOf(path)));
//        String line;
//        String content="";
//        while ((line=reader.readLine())!=null){
//            content=content+line;
//        }
//        String[] splitcontent=content.split("\\|");
////        for (String s:splitcontent){
////            System.out.println(s);
////        }
//
//        List<Entity> entityList=new ArrayList<>();
//        while ((line=reader.readLine())!=null){
//            Entity entity;
//            String[] values=line.trim().split("[,:]");
//            String businessRelation=values[1];
//            switch (businessRelation.toLowerCase()){
//                case "externalprivate":
//                    entity=new ExternalPrivateEntity(values[3],values[5],values[7]);
//                    entityList.add(entity);
//                    break;
//                case "externalbusiness":
//                    entity=new ExternalBusinessEntity(values[3],values[5],values[7],values[9]);
//                    entityList.add(entity);
//                    break;
//                case "internal":
//                    entity=new InternalEntity(values[3],values[5],values[7],InternalTeam.valueOf(values[9]),Boolean.parseBoolean(values[11]));
//                    entityList.add(entity);
//                    break;
//            }
//        }
//        for (Entity e:entityList){
//            String _class;
//            _class=e.getClass().toString();
//            String[] strings=_class.split("[ .]");
//            System.out.println(e.getClass().toString().split("[ .]")[strings.length-1]);
////            System.out.println(e);
//        }
    }
}
