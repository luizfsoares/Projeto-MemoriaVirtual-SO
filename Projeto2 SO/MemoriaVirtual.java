/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoriavirtual;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MemoriaVirtual {
    
    static int numQuadros;
    static int faltaPag = 0;
    static int tamanhoRef;
    static ArrayList<Integer> filaocorrencias1 = new ArrayList<Integer>();
    static ArrayList<Integer> filaocorrencias2 = new ArrayList<Integer>();
    static ArrayList<Integer> filafaltando = new ArrayList<Integer>();
    static int vezSair; // sair de forma circular
    
    public static void Fifo(int[] referencias){
        int x = 0;
        ArrayList<Integer> quadros = new ArrayList<>();
        for(int i = 0; i < tamanhoRef; i++){
            vezSair = (x % numQuadros);
                
                    if((!quadros.contains(referencias[i])) && (quadros.size() != numQuadros)){
                        quadros.add(referencias[i]);
                        faltaPag++; // primeira referencia da falta de pag
                    }
                    else{
                        if((!quadros.contains(referencias[i])) && (quadros.size() == numQuadros)){
                            quadros.set(vezSair, referencias[i]);
                            faltaPag++;
                            x++;
                        }
                    }
                    
                }
            
        System.out.println("FIFO " + faltaPag);
        }
    
    public static void LRU (int[] referencias){
        ArrayList<Integer> quadros = new ArrayList<>();
        faltaPag = 0;
        
        for(int i = 0; i < tamanhoRef; i++){
            if ((!quadros.contains(referencias[i])) && (quadros.size() != numQuadros)){
                quadros.add(referencias[i]);
                faltaPag++;
            }
            else if((!quadros.contains(referencias[i])) && (quadros.size() == numQuadros)){
               int aux = i - 1;
               int count = 0;
               for(int j = aux; j >=0; j--){ // loop decrescente
                   for(int x = 0; x < numQuadros; x++){
                       if((referencias[j]==quadros.get(x)) && (!filaocorrencias1.contains(x))){
                           filaocorrencias1.add(x);
                           count++;
                           //System.out.println(filaocorrencias);
                           break; // sai do 3° loop
                       }
                       
                   }
                   if (count == numQuadros){
                       break;
                   }//sai do 2° loop 
               }
               quadros.set(filaocorrencias1.get(numQuadros - 1), referencias[i]);
               faltaPag++; 
               for(int x = 0; x < numQuadros; x++){
                   filaocorrencias1.remove(0); //zera fila de ocorrencias pra proxima iteração
               }
               
           }
            //System.out.println(quadros);
        }
        System.out.println("LRU " + faltaPag);}
    
    public static void algOtimo(int[] referencias){
        ArrayList<Integer> quadros = new ArrayList<>();
        faltaPag = 0;

        
        for(int i = 0; i < tamanhoRef; i++){
            
            if ((!quadros.contains(referencias[i])) && (quadros.size() != numQuadros)){
                quadros.add(referencias[i]);
                faltaPag++;
            }
            
            else if ((!quadros.contains(referencias[i])) && (quadros.size() == numQuadros)){
                int aux = i + 1;
                int count = 0;
                for(int j = aux; j < tamanhoRef; j++){ // loop decrescente
                   for(int x = 0; x < numQuadros; x++){
                       if((referencias[j]==quadros.get(x)) && (!filaocorrencias2.contains(x))){
                           filaocorrencias2.add(x);
                           count++;
                           //System.out.println(filaocorrencias);
                           break; // sai do 3° loop
                       }
                       else {
                           filafaltando.add(x); // se nao pertence a fila de ocorrencias vem pra ca pra ser usado como maior distancia 
                       }     
                   }
                   if (count == numQuadros){ //todas as primeiras ocorrencias dos quadros
                       break;
                   }//sai do 2° loop 
                   
               }
                if (count == numQuadros){ 
                    quadros.set(filaocorrencias2.get(numQuadros - 1), referencias[i]);
                    faltaPag++;
                }
                else{
                    quadros.set(filafaltando.get(0), referencias[i]);
                    faltaPag++;
                }
                
            }
            //System.out.println(quadros);
                 
    }
        System.out.println("OTM " + faltaPag);
    }


    
    public static void main(String args[]) throws IOException {
        Path path = Paths.get("entrada.txt");
        List<String> linhasArquivo = Files.readAllLines(path);
        
        numQuadros = Integer.parseInt(linhasArquivo.get(0));
        tamanhoRef = (linhasArquivo.size() - 1);
        int[] referencias = new int[linhasArquivo.size() - 1];
        for (int i = 1; i < linhasArquivo.size(); i++){
            referencias[i-1] = Integer.parseInt(linhasArquivo.get(i));
        }
        Fifo(referencias);
        algOtimo(referencias);
        LRU(referencias);
        
    }
}