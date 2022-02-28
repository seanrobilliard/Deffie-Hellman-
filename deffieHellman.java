import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.Math.pow;

public class deffieHellman {

    private static int p;
    private static int a;




    public static void main(String[] args)
    {


        p = generatePrime(); //generate random prime with testing for prime included
        System.out.println("Prime number= " +p);

        a = findPrimitive(p);  //find primative roots of this prime number
        System.out.println("Primitive root =" +a);

        //Diffie Hellman  key exchange
        long XA =  22;
        System.out.println("Alice's secret number is (XA) " +XA);
        long YA = generator(XA); //generate key
        long XB = 7;
        long YB = generator(XB);  //generate key
        System.out.println("Bob's secret number is (XB) " +XB);
        double secretKEY1 = keyGenerator(YB ,XA);
        double secretKEY2 = keyGenerator(YA ,XB); //calculates values Y = a X mod p

       System.out.println("Secret key 1 = " + secretKEY1+ " Secret key 2 = " +secretKEY2);

        //man in the middle ttack
        System.out.println("Man in the middle attack" );
        long Malory =1;
        long MaloryA = generator(Malory);
        double secretKEY3 = keyGenerator(YB, MaloryA);
        double secretKEY4 = keyGenerator(YA ,MaloryA);


        System.out.println("Mallory key 1 = " + secretKEY3+ " Mallory key 2 = " +secretKEY4);


        //code that retrieve XA (using YA, p and a) and XB (using YB, p and a).
        System.out.println("Retrieving XA using YA "  );
        System.out.println("XA =  " +generatorBruteforce(YA)  );


        System.out.println("Retrieving XB using YB "  );
        System.out.println("XB =  " +generatorBruteforce(YB)  );





    }


//code that retrieve XA (using YA, p, and a) and XB (using YB, p and a).

    private static long  generatorBruteforce(long X) {
        for (int i = 0; i < p; i++) {

            long Y = (long) ((pow(a, i)) % p);

            if (Y == X) return i;


        }


        return X;
    }




    private static long  generator(long X) {

       long  Y = (long)(( pow ( a, X ) ) % p);

        return Y;



    }




    private static long  keyGenerator(long X ,long Y) {




        long key = (long) (( pow ( X, Y ) ) % p);



      return key ;



    }


    private static int generatePrime() {

//method to create and test random prime number
        long randomNum = 0;
        boolean isPrime = false;

          while (isPrime ==false) {
               randomNum = Math.round(Math.random() * 89999) + 10000;


           isPrime =    isPrime((int) randomNum);
          }

          return (int) randomNum;



    }






        // Returns true if n is prime
        static boolean isPrime(int n)
        {
            // Corner cases
            if (n <= 1)
            {
                return false;
            }
            if (n <= 3)
            {
                return true;
            }

            // This is checked so that we can skip
            // middle five numbers in below loop
            if (n % 2 == 0 || n % 3 == 0)
            {
                return false;
            }

            for (int i = 5; i * i <= n; i = i + 6)
            {
                if (n % i == 0 || n % (i + 2) == 0)
                {
                    return false;
                }
            }

            return true;
        }




        /* Iterative Function to calculate (x^n)%p in
        O(logy) */
        static int power(int x, int y, int p)
        {
            int res = 1;	 // Initialize result

            x = x % p; // Update x if it is more than or
            // equal to p

            while (y > 0)
            {
                // If y is odd, multiply x with result
                if (y % 2 == 1)
                {
                    res = (res * x) % p;
                }

                // y must be even now
                y = y >> 1; // y = y/2
                x = (x * x) % p;
            }
            return res;
        }

        // Utility function to store prime factors of a number
        static void findPrimefactors(HashSet<Integer> s, int n)
        {
            // Print the number of 2s that divide n
            while (n % 2 == 0)
            {
                s.add(2);
                n = n / 2;
            }

            // n must be odd at this point. So we can skip
            // one element (Note i = i +2)
            for (int i = 3; i <= Math.sqrt(n); i = i + 2)
            {
                // While i divides n, print i and divide n
                while (n % i == 0)
                {
                    s.add(i);
                    n = n / i;
                }
            }

            // This condition is to handle the case when
            // n is a prime number greater than 2
            if (n > 2)
            {
                s.add(n);
            }
        }

        // Function to find smallest primitive root of n
        static int findPrimitive(int n)
        {
            HashSet<Integer> s = new HashSet<Integer>();

            // Check if n is prime or not
            if (isPrime(n) == false)
            {
                return -1;
            }

            // Find value of Euler Totient function of n
            // Since n is a prime number, the value of Euler
            // Totient function is n-1 as there are n-1
            // relatively prime numbers.
            int phi = n - 1;

            // Find prime factors of phi and store in a set
            findPrimefactors(s, phi);

            // Check for every number from 2 to phi
            for (int r = 2; r <= phi; r++)
            {
                // Iterate through all prime factors of phi.
                // and check if we found a power with value 1
                boolean flag = false;
                for (Integer a : s)
                {

                    // Check if r^((phi)/primefactors) mod n
                    // is 1 or not
                    if (power(r, phi / (a), n) == 1)
                    {
                        flag = true;
                        break;
                    }
                }

                // If there was no power with value 1.
                if (flag == false)
                {
                    return r;
                }
            }

            // If no primitive root found
            return -1;
        }


    }

