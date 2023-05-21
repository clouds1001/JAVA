import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Objects;

public class YAHOO {
    public static void main(String[] args) {
        try{
            String URL_1 = "https://tw.stock.yahoo.com";
            String URL_2 = "";
            String URL_3 = "";

            Document index_1 = Jsoup.connect(URL_1).get();
            Document index_2 = new Document("");
            Document index_3 = new Document("");
            Elements headline = index_1.select("div #ybar-navigation li._yb_1ch9l a._yb_10mdq");
            for (Element x : headline)
            {
                if (Objects.equals(x.text(),"當日行情"))
                {
                    System.out.println("YAHOO股票首頁網址:"+x.attr("href"));
                    System.out.println();
                    URL_2 = x.attr("href");
                    index_2=Jsoup.connect(URL_2).get();
                    break;
                }
            }
            System.out.println(index_2.title());

            Elements headline2 = index_2.select("div #LISTED_STOCK li div a");

            for (Element x : headline2)
            {
                if (Objects.equals(x.text(),"半導體"))
                {
                    URL_3 = URL_1+x.attr("href");
                    System.out.println(URL_1+x.attr("href"));
                    index_3=Jsoup.connect(URL_3).get();
                    break;
                }
            }

            Elements comp_main = index_3.select("div[class~=^Pos]");
            Elements cont = comp_main.select("div[class~=^Fxg]");
            //    Elements comp_zf = cont.select("div[class~=^Pos]");
            int num=0;
            System.out.println(index_3.title());
            int num1 = 9;

            for (Element a : comp_main)
            {
                Element comp = a.select("div[class=Lh(20px) Fw(600) Fz(16px) Ell]").first();
                Element comp_code = a.select("span[class=Fz(14px) C(#979ba7) Ell]").first();
                if (comp_code==null || comp==null)
                    continue;
                String Sum_text=comp.text()+comp_code.text();

                int len=15;
                if (num==0)
                {
                    Sum_text="";
                    len=27;
                    System.out.print(rightPadding(Sum_text,len,' '));

                    for (int i=0;i<=8;i++,num++)
                    {
                        System.out.printf("%-8s ",cont.get(num).text());

                    }
                    System.out.println();
                    continue;
                }

                System.out.print(rightPadding(Sum_text,len,'　'));

                for (int i=0;i<=8;i++,num++)
                {
                    System.out.printf("%10s ",cont.get(num).text());
                }


                String j = cont.get(num1+4).text();
                String b = cont.get(num1).text();
                String result = j.replaceAll("\\p{Punct}", "");
                String result2 = b.replaceAll("\\p{Punct}", "");
                double s = Double.parseDouble(result.trim());
                double k = Double.parseDouble(result2.trim());

                num1 = num1 +9;

                while (s <= k){
                    System.out.println("漲");
                    break;
                }

                while(s > k){
                    System.out.println("跌");
                    break;
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String rightPadding(String str, int length, char padChar) {
        //string + (Length - string.length() )*padChar
        if(str == null) {
            str = "";
        }
        if (str.length() > length) {
            return str;
        }
        String pattern = "%-" + length + "s";
        return String.format(pattern, str).replace(' ', padChar);
    }
}