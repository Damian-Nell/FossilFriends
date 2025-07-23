package Main.src.Managers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Dinosaur {

    /*
        * all the variables for the dinosaur class, self-explanatory.
     */
    private int saveNo, LHunger, LThirst, LClean, LAge, dinoType, LHappiness, LLonely, hCoolDown, tCoolDown, cCoolDown;
    private double dHunger, dThirst, dClean, dLonely, statMulti;
    private String DName;
    private boolean death, egg;
    private LocalDateTime startDateTime, lastDateTime;

    /*
        * constructor method to create the dinosaur using all the variables.
     */
    public Dinosaur(int inSaveNo, String inName, int inType, int inHunger, int inThirst, int inClean, int inAge, int inLonely, boolean inDeath, boolean inEgg, LocalDateTime inStart, LocalDateTime inLast) {
        saveNo = inSaveNo;
        DName = inName;
        dinoType = inType;
        LHunger = inHunger;
        LThirst = inThirst;
        LClean = inClean;
        LAge = inAge;
        LLonely = inLonely;                                                     //lower means more lonely. higher means less lonely/more overwhelmed.
        death = inDeath;
        egg = inEgg;                                                            //false mean still inside the egg. true means egg has hatched.
        startDateTime = inStart;
        lastDateTime = inLast;

        dHunger = LHunger;
        dThirst = LThirst;
        dClean = LClean;
        dLonely = LLonely;
    }

    /*
        * Updates the stats of this specific dino. Takes in:
        *       - timeSinceLast - to determine how much to decrease.
        *       - online - to determine whether the game was online or offline during the time. 
        * it will first set the age of the Dinosaur
        * then decrease the cooldown values.
        * then check if it was online or offline and adjust those stats accordingly
        * then will check if the Dinosaur has died
    */
    public void updateStats(double timeSinceLast, boolean online) {
        check();
        Period p = Period.between(getStartDate(), LocalDate.now());
        setAge(p.getYears() * 365
                + p.getMonths() * 30
                + p.getDays());

        hCoolDown -= timeSinceLast;
        tCoolDown -= timeSinceLast;
        cCoolDown -= timeSinceLast;

        if (online == true) {

            dHunger -= (timeSinceLast / (150)) * statMulti;
            dThirst -= (timeSinceLast / (125)) * statMulti;
            dClean -= (timeSinceLast / (250)) * statMulti;
            lastDateTime = LocalDateTime.now();

            if (dLonely < 100) {
                dLonely += (timeSinceLast / (225));
            }

            LHunger = (int) Math.round(dHunger);
            LThirst = (int) Math.round(dThirst);
            LClean = (int) Math.round(dClean);
            LLonely = (int) Math.round(dLonely);

        } else if (online == false) {

            dHunger -= (timeSinceLast / (1500)) * statMulti;
            dThirst -= (timeSinceLast / (1250)) * statMulti;
            dClean -= (timeSinceLast / (2500)) * statMulti;
            lastDateTime = LocalDateTime.now();

            if (dLonely-(timeSinceLast / (170)) > 0) {
                dLonely -= (timeSinceLast / (170));
            } else{
                dLonely = 0;
            }

            LHunger = (int) Math.round(dHunger);
            LThirst = (int) Math.round(dThirst);
            LClean = (int) Math.round(dClean);
            LLonely = (int) Math.round(dLonely);
        }

        if (LHunger <= 0 || LThirst <= 0 || LClean <= 0) {
            setDead(true);
        }
    }
    
    //Data validation on the dinosaur
    private void check(){
        if (getHunger() > 100){
            setHunger(100);
        } else if (getHunger() < 0){
            setHunger(0);
        }
        if (getThirst() > 100) {
            setThirst(100);
        }else if (getThirst() < 0){
            setThirst(0);
        }
        if (getClean() > 100){
            setClean(100);
        }else if (getClean() < 0){
            setClean(0);
        }
        if(getLonely() > 100){
            setLonely(100);
        }else if (getLonely() < 0){
            setLonely(0);
        }
        if (getType() != 1 && getType() != 2 && getType() != 3){
            dinoType = (int) (Math.random() * 3) + 1;
        }
    }

    //getter methods
    public int getNo() {
        return saveNo;
    }

    public String getName() {
        return DName;
    }

    public int getType() {
        return dinoType;
    }

    public int getHunger() {
        return LHunger;
    }

    public int getThirst() {
        return LThirst;
    }

    public int getClean() {
        return LClean;
    }

    public int getAge() {
        return LAge;
    }

    public int getLonely() {
        return LLonely;
    }

    public Boolean getDeath() {
        return death;
    }

    public String getStart() {
        return startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss"));
    }

    public String getLast() {
        return lastDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss"));
    }

    public String getStartRaw() {
        return startDateTime.toString();
    }

    public String getLastRaw() {
        return lastDateTime.toString();
    }

    public int getHCool() {
        return hCoolDown;
    }

    public int getTCool() {
        return tCoolDown;
    }

    public int getCCool() {
        return cCoolDown;
    }

    //returns the total amount of seconds since the last update.
    public double secsSinceLast() {
        double last = ((lastDateTime.getYear() * 365 * 30 * 24 * 3600) + (lastDateTime.getMonthValue() * 30 * 24 * 3600) + (lastDateTime.getDayOfMonth() * 24 * 3600)
                + (lastDateTime.getHour() * 3600) + (lastDateTime.getMinute() * 60) + (lastDateTime.getSecond()));
        double current = (LocalDateTime.now().getYear() * 365 * 30 * 24 * 3600) + (LocalDateTime.now().getMonthValue() * 30 * 24 * 3600) + (LocalDateTime.now().getDayOfMonth() * 24 * 3600)
                + (LocalDateTime.now().getHour() * 3600) + (LocalDateTime.now().getMinute() * 60) + (LocalDateTime.now().getSecond());
        return current - last;
    }

    public LocalDate getStartDate() {
        return LocalDate.of(startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDayOfMonth());
    }

    /*
        * creates the hapiness as the average of the other stats with specific values for the loneliness
        * (0 means the dinosaur feels lonely and 100 means the dinosaur feels overwhelmed. sweet spot is between 60 and 70)
     */
    public int getHappiness() {
        if (LLonely > 60 && LLonely < 70) {
            LHappiness = (LHunger + LThirst + LClean + 100) / 4;
        } else if (LLonely > 50 && LLonely < 80) {
            LHappiness = (LHunger + LThirst + LClean + 80) / 4;
        } else if (LLonely > 30 || LLonely < 90) {
            LHappiness = (LHunger + LThirst + LClean + 50) / 4;
        } else {
            LHappiness = (LHunger + LThirst + LClean + 0) / 4;
        }
        return LHappiness;
    }
    
    public boolean getEgg(){
        return egg;
    }

    //setter methods
    public void setHunger(int hunger) {
        LHunger = hunger;
        dHunger = LHunger;
    }

    public void setThirst(int thirst) {
        LThirst = thirst;
        dThirst = LThirst;
    }

    public void setClean(int clean) {
        LClean = clean;
        dClean = LClean;
    }

    public void setAge(int age) {
        LAge = age;
    }

    public void setLonely(int Lonely) {
        LLonely = Lonely;
        dLonely = LLonely;
    }

    public void setDead(boolean dead) {
        death = dead;

        if (dead == true) {
            LHunger = 1;
            LThirst = 1;
            LClean = 1;
            LLonely = 1;
        }
    }

    public void setLast(LocalDateTime dateTime) {
        lastDateTime = dateTime;
    }

    public void setHCool(int hCool) {
        hCoolDown = hCool;
    }

    public void setTCool(int tCool) {
        tCoolDown = tCool;
    }

    public void setCCool(int cCool) {
        cCoolDown = cCool;
    }

    public void setStatMulti(double StatMulti) {
        statMulti = StatMulti;
    }
    
    public void setEgg(boolean inEgg){
        egg = inEgg;
    }
}
