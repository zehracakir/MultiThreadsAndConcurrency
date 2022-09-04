package Thread;
import java.util.ArrayList;

public class ThreadClass implements Runnable {
    private int firstNumber = 1;

    private final Object LOCK = new Object();
    private ArrayList<Integer> dividedList = new ArrayList<>();;
    private ArrayList<Integer> dividedList2 = new ArrayList<>();;
    private ArrayList<Integer> dividedList3 = new ArrayList<>();;
    private ArrayList<Integer> dividedList4 = new ArrayList<>();;
    private ArrayList<Integer> oddNumbers = new ArrayList<>();;
    private ArrayList<Integer> evenNumber = new ArrayList<>();;

    public ThreadClass() {

    }

    @Override
    public void run() {

        synchronized (LOCK) {
            System.out.println(getFirstNumber());

            if (this.firstNumber <= 2500) {
                this.dividedList.add(this.firstNumber);
                System.out.println(this.firstNumber + " " + "added to dividedList1");
            } else if (this.firstNumber > 2500 && this.firstNumber <= 5000) {
                this.dividedList2.add(this.firstNumber);
                System.out.println(this.firstNumber + " " + "added to dividedList2");
            } else if (this.firstNumber > 5000 && this.firstNumber <= 7500) {
                this.dividedList3.add(this.firstNumber);
                System.out.println(this.firstNumber + " " + "added to dividedList3");
            } else {
                this.dividedList4.add(this.firstNumber);
                System.out.println(this.firstNumber + " " + "added to dividedList4");
            }

            if (this.firstNumber % 2 == 0) {
                this.evenNumber.add(this.firstNumber);
                System.out.println(this.firstNumber + " " + "added to Even Number List");
            } else {
                this.oddNumbers.add(this.firstNumber);
                System.out.println(this.firstNumber + " " + "added to Odd Number List");
            }
            this.firstNumber++;
        }

    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public ArrayList<Integer> getDividedList() {
        return dividedList;
    }

    public ArrayList<Integer> getDividedList2() {
        return dividedList2;
    }

    public synchronized ArrayList<Integer> getDividedList3() {
        return dividedList3;
    }

    public synchronized ArrayList<Integer> getDividedList4() {
        return dividedList4;
    }

    public synchronized ArrayList<Integer> getOddNumbers() {
        return oddNumbers;
    }

    public synchronized ArrayList<Integer> getEvenNumber() {
        return evenNumber;
    }
}