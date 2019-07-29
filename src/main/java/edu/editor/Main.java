package edu.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu.editor/mainView.fxml"));
        primaryStage.setTitle("Automata Editor");
        primaryStage.setScene(new Scene(root, 1500, 800));
        primaryStage.show();


//        Set<Integer> set = new BitSet<>(BitSet.ENUM.INTEGER);
//        set.insert(2);
//        set.insert(3);
//        set.insert(4);
//        set.insert(5);
//        set.insert(2);
//        set.insert(11);
//        System.out.println("Size of set1: " + Long.bitCount(set.getSet()));
//
//        Set<Integer> set2 = new BitSet<>(BitSet.ENUM.INTEGER);
//        set2.insert(21);
//        set2.insert(3);
//        set2.insert(4);
//        set2.insert(52);
//        set2.insert(21);
//        set2.insert(11);
//        System.out.println("Size of set2: " + Long.bitCount(set2.getSet()));
//
//
//        Set<Set> setSet = new BitSet<>(BitSet.ENUM.SET_INTEGER);
//        setSet.insert(set);
//        setSet.insert(set2);
//
//        List<Set> list = setSet.getElements();
//
//        for(Set p : list){
//           List<Integer> l = p.getElements();
//            for(Integer i : l){
//                System.out.println(i);
//            }
//
//            System.out.println();
//            System.out.println();
//        }
//
////        Set<Integer> unionSet = set.union(set2);
////        System.out.println("Size of unionSet: " + unionSet.getSize());
////        System.out.println(set.contains(11));
////
////        List<Integer> s = unionSet.getElements();
////        for(Object p : s){
////            System.out.println(p);
////        }

    }
}
