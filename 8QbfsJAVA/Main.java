package mainpkg.test;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FindAnswer fa = new FindAnswer();

        /*каждый символ - ряд, число - колонна*/
        //Scanner sc = new Scanner(System.in);
        //String str = sc.next();41055373  02406070  77777777
        //sc.close();

        String[] strs = {
                "41055373",
                "02406070",
                "77777777",
                "12312312",
                "46372513",//5
                "15731233",
                "64134123",
                "12412531",
                "61241254",
                "12532123",//10
                "11245121",
                "13513131",
                "12423212",
                "42342342",
                "72341241",//15
                "32523423",
                "23423411",
                "11245121",
                "41055373",
                "23423421" //20
        };
        for (String arr: strs) {
            FindAnswer fafor = new FindAnswer();
            Board temp = new Board(arr);
            System.out.println(temp);
            temp = fafor.performBFS(temp);
            System.out.println(temp);
            System.out.println("--------------------end-----------------\n");
        }

        /*
        Board test = new Board("01234567");
        System.out.println(test);
        test = fa.performBFS(test);
        System.out.println(test);
        //String str = "77777777";
         */

        /*
        Board b = new Board(str);
        System.out.println(b);
        Board w = fa.performBFS(b);
        System.out.println(w);
         */
    }
}
