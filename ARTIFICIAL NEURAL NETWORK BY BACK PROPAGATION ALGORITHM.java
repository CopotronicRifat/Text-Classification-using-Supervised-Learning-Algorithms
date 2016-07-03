/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stanford.database;

/**
 *
 * @author Copotronic Rifat
 */
import java.lang.Math;
import java.io.File;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.String;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.util.Locale;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.Math;
import java.io.File;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.String;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.util.Locale;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.trees.Dependencies;
import edu.stanford.nlp.util.Triple;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

import java.util.List;
public class STANFORDDATABASE
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassCastException, ClassNotFoundException
    {
        // TODO code application logic here
        File yfile=new File("E:\\EIGHTH SEMESTER\\PROJECT AND THESIS II\\SOFTWARE\\WORDNET\\document.txt");
        FileWriter fwriter=new FileWriter(yfile,true);
        BufferedWriter buffer=new BufferedWriter(fwriter);
        PrintWriter pwriter=new PrintWriter(buffer);
        String wordnet[]=new String[15000];
        String pos[]=new String[20];
        int post_train[]=new int[15000];
        int post[]=new int[20];
        int a=0;
        int n=0;
        MaxentTagger tag=new MaxentTagger("C:\\Users\\Copotronic Rifat\\DocumentsNETBEANS\\STANFORD DATABASE\\src\\stanford\\database\\models\\english-bidirectional-distsim.tagger");
        //MaxentTagger tag2=new MaxentTagger("C:\\Users\\Copotronic Rifat\\DocumentsNETBEANS\\STANFORD DATABASE\\src\\stanford\\database\\models\\english-left3words-distsim.tagger");
        String serializedClassifier=("C:\\Users\\Copotronic Rifat\\DocumentsNETBEANS\\STANFORD DATABASE\\src\\stanford\\database\\classifiers\\english.all.3class.distsim.crf.ser.gz");
        AbstractSequenceClassifier classifyinput=CRFClassifier.getClassifier(serializedClassifier);
        String sentence=JOptionPane.showInputDialog("Type a sentence and click OK: ");
        String s1=tag.tagString(sentence);
        //String s2=tag2.tagString(sentence);
        StringTokenizer tokens= new StringTokenizer(sentence);
        while(tokens.hasMoreTokens())
        {
            pos[a]=tokens.nextToken();
            a++;
        }
        int m=0;
        Scanner scanner_wordnet;
        int k=0;
        try
        {
            scanner_wordnet=new Scanner(new File("E:\\EIGHTH SEMESTER\\PROJECT AND THESIS II\\SOFTWARE\\WORDNET\\document.txt"));
            while(scanner_wordnet.hasNext())
            {
                wordnet[k]=scanner_wordnet.next();
                k++;
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Could not open file or file does not exist!!\n");
        }
        for(int b=0; b<k; b++)
        {
            String tagged_train= tag.tagString(wordnet[b]);
            m++;
            if(tagged_train.endsWith("NN "))//proper noun Alison, Africa, April, Washington
            {
                post_train[b]=0;
                continue;
            }
            else if(tagged_train.endsWith("NNS "))//noun year, home, costs, time, education
            {
                post_train[k]=0;
                continue;
            }
            else if(tagged_train.endsWith("ADJ "))//adjective new, good, high, special, big, local
            {
                post_train[k]=1;
                continue;
            }
            else if(tagged_train.endsWith("ADV "))//ADVERB really, already, still, early, now
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("CNJ "))//conjunction and, or, but, if, while, although
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("DET "))//determiner the, a, some, most, every, no
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("EX "))//there, there’s
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("MD "))//MOD modal verb will, can, would, may, must, should
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("NUM "))//number twenty-four, fourth, 1991, 14:24
            {
                post_train[b]=0;
                continue;
            }
            else if(tagged_train.endsWith("PRP "))//pronoun he, their, her, its, my, I, us
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("TO "))//the word to to
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("UH "))//interjection ah, bang, ha, whee, hmpf, oops
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("VB "))//verb is, has, get, do, make, see, run
            {
                //post_train[b]=12;
                continue;
            }
            else if(tagged_train.endsWith("VD "))//past tense said, took, told, made, asked
            {
                //post_train[b]=13;
                continue;
            }
            else if(tagged_train.endsWith("VG "))//present participle making, going, playing, working
            {
                //post_train[b]=14;
                continue;
            }
            else if(tagged_train.endsWith("VD "))//VN past participle given, taken, begun, sung
            {
                //post_train[b]=15;
                continue;
            }
            else if(tagged_train.endsWith("RB "))
            {
                post_train[b]=1;
                continue;
            }
            else if(tagged_train.endsWith("WRB "))
            {
                post_train[b]=1;
                continue;
            }
            else
            {
                //post_train[b]=18;
                continue;
            }
        }
        for(int l=0; l<10; l++)
        {
            System.out.println("Feature Vector :"+post_train[l]);
        }
        for(int i=0; i<a; i++)
        {
            String tagged= tag.tagString(pos[i]);
            System.out.println("Tagged :"+tagged);
            n++;
            if(tagged.endsWith("NN "))//NP proper noun Alison, Africa, April, Washington
            {
                post[i]=0;
                continue;
            }
            else if(tagged.endsWith("NNS "))//noun year, home, costs, time, education
            {
                post[i]=0;
                continue;
            }
            else if(tagged.endsWith("ADJ "))//adjective new, good, high, special, big, local
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("ADV "))//really, already, still, early, now
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("CNJ "))//conjunction and, or, but, if, while, although
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("DET "))//determiner the, a, some, most, every, no
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("EX "))//there, there’s
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("MD "))//MOD modal verb will, can, would, may, must, should
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("NUM "))//number twenty-four, fourth, 1991, 14:24
            {
                post[i]=0;
                continue;
            }
            else if(tagged.endsWith("PRP "))//pronoun he, their, her, its, my, I, us
            {
                post[i]=0;
                continue;
            }
            else if(tagged.endsWith("TO "))//the word to to
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("UH "))//interjection ah, bang, ha, whee, hmpf, oops
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("VB "))//verb is, has, get, do, make, see, run
            {
                //post[i]=12;
                continue;
            }
            else if(tagged.endsWith("VD "))//past tense said, took, told, made, asked
            {
                //post[i]=13;
                continue;
            }
            else if(tagged.endsWith("VG "))//present participle making, going, playing, working
            {
                //post[i]=14;
                continue;
            }
            else if(tagged.endsWith("VD "))//VN past participle given, taken, begun, sung
            {
                //post[i]=15;
                continue;
            }
            else if(tagged.endsWith("RB "))
            {
                post[i]=1;
                continue;
            }
            else if(tagged.endsWith("WRB "))
            {
                post[i]=1;
                continue;
            }
            else
            {
                //post[i]=18;
                continue;
            }
        }
        for(int j=0; j<n; j++)
        {
            System.out.println("Feature Vector :"+post[j]);
        }
        JOptionPane.showMessageDialog(null,s1+"\n");
        double OverallError;
        double MinimumError;
        double ExpectedOutput[][];
        double Input[][];
        double LearningRate;
        double Momentum;
        int NumberOfLayers;
        int NumberOfSamples;
        int SampleNumber;
        long MaximumNumberOfIterations;
        int Layer[];
        double ActualOutput[][];
        int I,N,J,A,K;
        double C;
        double weightIJ[][]=new double[30][30];
        double weightJK[][]=new double[30][30];
        double uhJ[]=new double[30];
        double activJ[]=new double [30];
        double uoK[]= new double[30];
        double oaJ[]=new double[30];
        double oaJ1;
        double netaJ[]=new double[30];
        double netaK[]=new double[30];
        double C1,C2;
        double activK[]=new double[30];
        double oaK1;
        double oaK[]=new double[30];
        double delaK[]=new double[30];
        double del,del1,sum;
        double delweightJK[][]=new double[30][30];
        double delweightIJ[][]=new double[30][30];
        double deluoK[]=new double[30];
        double deluhJ[]=new double[30];
        int input=n;
        int hidden=3;
        int output=2;
        for(I=0; I<input; I++)
        {
            for(J=0; J<hidden; J++)
            {
                weightIJ[I][J]=(double)(Math.random()%10)/10;
                System.out.println("Weights between the nodes of input and hidden layer: "+weightIJ[I][J]);
            }
        }
        for(I=0; I<hidden; I++)
        {
            for(J=0; J<output; J++)
            {
                weightJK[I][J]=(double)(Math.random()%10)/10;
                System.out.println("Weights between the nodes of hidden and output layer: "+weightJK[I][J]);
            }

        }
        for(J=0; J<hidden; J++)
        {
            uhJ[J]=(double)(Math.random()%10)/10;
            System.out.println("uhJ: "+uhJ[J]);
        }
        for(K=0; K<output; K++)
        {
            uoK[K]=(double)(Math.random()%10)/10;
            System.out.println("uoK "+uoK[K]);
        }
        double eta1=0.1;
        double eta2=0.1;
        double k1=0.4;
        double k2=0.5;
        int o[]=new int[n];
        for(int l=0; l<n; l++)
        {
            o[l]=post[l];
        }
        int tar[]={0,1};


        for(A=0; A<1; A++)
        {
            C=0;
            do
            {
                for(J=0; J<hidden; J++)
                {
                    C1=0;
                    for(I=0; I<input; I++)
                    {
                        C1+=weightIJ[J][I]*o[I];
                    }
                    netaJ[J]=C1;
                }
                for(J=0; J<hidden; J++)
                {
                    activJ[J]=netaJ[J]+uhJ[J];
                    System.out.println("activJ of node "+J+" is "+activJ[J]);
                    oaJ1=1+Math.exp(-(k1*activJ[J]));
                    oaJ[J]=1/oaJ1;
                    System.out.println("Oaj of node "+J+" is "+oaJ[J]);
                }
                for(J=0; J<hidden; J++)
                {
                    C2=0;
                    for(K=0; K<output; K++)
                    {
                        C2+=weightJK[J][K]*oaJ[J];
                        netaK[J]=C2;
                    }
                    System.out.println("netaK of node "+J+" is "+netaK[J]);
                }
                for(K=0; K<output; K++)
                {
                    activK[K]=netaK[K]+uoK[K];
                    System.out.println("activK of node "+K+" is "+activK[K]);
                    oaK1=1+Math.exp(-(k2*activK[K]));
                    oaK[K]=1/oaK1;
                    System.out.println("Oak of node"+K+" is "+oaK[K]);
                }
                n=(int) Math.floor(A/input);
                System.out.println("n="+n);
                del1=0;
                for(K=0; K<output; K++)
                {
                    delaK[K]=tar[K]-oaK[K];
                    del1+=delaK[K];
                }
                del=del1/output;

                System.out.println("Delak1: "+delaK[0]);
                System.out.println("Delak2: "+delaK[1]);
                System.out.println("Del: "+del);
                for(J=0; J<hidden; J++)
                {
                    for(K=0; K<output; K++)
                    {
                        delweightJK[J][K]=eta2*k2*delaK[K]*oaJ[J]*oaK[K]*(1-oaK[K]);
                        System.out.println("delweightJK of node "+J+" and "+K+" is "+delweightJK[J][K]);
                        weightJK[J][K]=weightJK[J][K]+delweightJK[J][K];
                        System.out.println("weightJK: "+weightJK[J][K]);
                    }
                    deluoK[J]=eta2*k2*delaK[K]*oaK[K]*(1-oaK[K]);
                    uoK[J]=uoK[J]+deluoK[J];
                    System.out.println("uoK: "+uoK[J]);
                }
                System.out.println("C: "+C);
                C++;
            }
            while(del>=0.000001);

        }

    }
}
