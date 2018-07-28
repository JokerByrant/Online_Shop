package entity;

public class test {
public static void main(String[] args) {
	java.util.Date date = new java.util.Date ();
    int data = (int) date.getTime();
  //自己生成地址id
    System.out.println(data);
    String id="6"+data+"";
    id=id.substring(0, 4)+id.substring(6,10);
    System.out.println(id);
    int addr_id=Integer.parseInt(id);
    System.out.println(addr_id);
   
}
}
