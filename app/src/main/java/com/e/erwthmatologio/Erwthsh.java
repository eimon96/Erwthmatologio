package com.e.erwthmatologio;

import java.util.ArrayList;

/**
 * Αντικείμενο Ερώτηση
 */
public class Erwthsh
{
    private String QueText;                 // Κείμενο Ερώτησης
    private ArrayList<String> Answers;      // ArrayList Πιθανών Απαντήσεων
    private int CorrectAns;                 // Αριθμός Σωστής Απάντησης
    private int UserAns;                    // Αριθμός Απάντησης Χρήστη
    private int Img;                        // ID Εικόνας

    /**
     * Δημιουργεί ένα νέο αντικείμενο Ερώτηση
     */
    public Erwthsh()
    {
        Answers = new ArrayList();
        CorrectAns = -1;
        UserAns = -1;
        Img = 0;
    }

    // Getters και Setters
    public String getQueText()
    {
        return QueText;
    }

    public void setQueText(String queText)
    {
        QueText = queText;
    }

    public int getCorrectAns()
    {
        return CorrectAns;
    }

    public void setCorrectAns(int correctAns)
    {
        CorrectAns = correctAns;
    }

    public int getUserAns()
    {
        return UserAns;
    }

    public void setUserAns(int userAns)
    {
        UserAns = userAns;
    }

    public String getAnswer(int AnsNum)
    {
        return Answers.get(AnsNum);
    }

    public int getImg()
    {
        return Img;
    }

    public void setImg(int img)
    {
        Img = img;
    }

    /**
     * Προσθέτει απάντηση στη λίστα απαντήσεων της ερώτησης
     * @param Ans: η απάντηση προς προσθήκη
     */
    public void AddAnswer(String Ans)
    {
        Answers.add(Ans);
    }

    /**
     * Ελέγχει αν η ερώτηση έχει απαντηθεί
     * @return true αν ναι, false αλλιώς
     */
    public boolean isAnswered()
    {
        if (UserAns == -1)
            return false;
        else
            return true;
    }

    /**
     * Ελέγχει αν η ερώτηση έχει απαντηθεί σωστά
     * @return true αν ναι, false αλλιώς
     */
    public boolean isCorrect()
    {
        return UserAns == CorrectAns;
    }

    /**
     * @return τον αριθμό των απαντήσεων της ερώτησης
     */
    public int GetNoAnswers()
    {
        return Answers.size();
    }
}

