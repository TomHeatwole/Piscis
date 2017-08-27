public class SampleBot{
    public SampleBot(){
        //nothing for now
    }

    public String processMatchInfo(String s){
        if(s.split(" ") == "action")
            return "check";
        return "";
    }

}
