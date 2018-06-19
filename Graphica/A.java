public class
A{
public static void main(String[] args){
double low = Double.parseDouble(args[0]);
double high = Double.parseDouble(args[1]);
double dx = Double.parseDouble(args[2]);

String out = "";

for(double x = low; x < high; x+=dx){
out += x + " " + f(x) + " ";
}

System.out.println(out);
}

private static double f(double x){
return Math.tan(x);
}
}
