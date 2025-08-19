package Main.src.Managers;

public class Settings {
    private int Vol;
    private String Lang;
    private boolean tutComp;
    
    public Settings(int vol, boolean tutcomp, String lang){
        Vol = vol;
        tutComp = tutcomp;
        Lang = lang;
    }
    
    public int getVol(){
        return Vol;
    }
    
    public boolean getTut(){
        return tutComp;
    }
    
    public String getLang(){
        return Lang;
    }
    
    public void setVol(int vol){
        Vol = vol;
    }
    
    public void setTut(boolean tutcomp){
        tutComp = tutcomp;
    }
    
    public void setLang(String lang){
        Lang = lang;
    }
}
