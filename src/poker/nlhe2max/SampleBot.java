public class SampleBot{
    public SampleBot(){
        //nothing for now
    }

    public String processMatchInfo(String s){
        System.out.println(s);
        if(s.split(" ")[0].equals("action"))
            return "check";
        return "";
    }

}
