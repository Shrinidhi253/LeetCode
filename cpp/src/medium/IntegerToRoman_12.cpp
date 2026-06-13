#include <string>
#include <iostream>
#include <math.h>
#include <unordered_map>
#include <vector>

using namespace std;

class IntegerToRomanSolution1_12 {
    /*
    Idea:
    Deal with "num" as multiples of 10 as they are easier to construct
    Get each digit in num and keep track of the place value of num using a "weight" variable
        - place value = 400, weight = 2 (since it is 10^2)

    if the digit is 0:
        -> do nothing.

    if the digit is 5 or 1:
        -> the weighted number/ place value number should be in the romanIntMap (like 1, 5, 10, 50, 100, 500, 1000)

    if the digit is 4 or 9:
        -> we handle the weighted number/ place value using subtractive rule (e.g. handling 400, 40, 9, 900, etc)

    else:
        -> we use the additive rule for the weighted num

    ----
    subtractive rule:
    for 4:
        get the closet number starting with 5 (for e.g. for 4 get 5, for 40 get 50, for 400 get 500...)
        subtract power of 10 from it
        for 4, subtract 10^0 (5 - 1)
        for 40, subtract 10^1 (50 - 10)
        for 400, subtract 10^2 (500 - 100) and so on

    for 9:
        get the closest power of 10 (for e.g for 9 get 10, for 90 get 100, ..)
        subtract the immediate lower powers of 10 from it
        for 9, subtract 10^0 (10 - 1)
        for 90, subtract 10^1 (100 - 1)

    additive rule:
    if digit > 5:
        get closest number starting with 5 (get 5 for 6, 7, 8 and 50 for 60, 70, 80...)
        add powers of 10 to it until you reach the actual number
        60 = 50 + 10
        70 = 50 + 10 + 10
        800 = 500 + 10^2 + 10^2 + 10^2

    if digit < 5:
        get closest power of 10 (get 1 for 2, 3 and 10 fro 20, 30)
        add powers of 10 to it until you reach the actual number
        2 = 1 + 10^0
        30 = 10 + 10^1 + 10^2 ... and so on
    */
    public:
    string intToRoman(int num) {
        unordered_map<int, string> romanIntMap = createRomanIntMap();

        string roman = "";
        int weight = 0;

        while (num > 0) {
            int digit = num % 10;

            if (digit == 1 || digit == 5) {
                roman = romanIntMap[digit * pow(10, weight)] + roman;
            }
            else if (digit == 4 || digit == 9) {
                roman = this->handleSubtractiveCase(digit, weight, romanIntMap) + roman;
            }
            else if (digit != 0) {
                roman = this->handleAdditiveCase(digit, weight, romanIntMap) + roman;
            }
            //Update weights
            weight++;
            num /= 10;
        }
        return roman;
    }

    //Cases for constructing the roman
    private:
    string handleSubtractiveCase(int digit, int weight, unordered_map<int, string> romanIntMap) {
        int minuend; //number that you are subtracting from
        const int subtrahend = pow(10,weight); //the number that you are subtracting

        if (digit == 4) {minuend = this->getClosestNumStartingWith5(weight);} //for 4, subtract from num starting with 5
        else {minuend = this->getClosestPowerOf10(digit, weight);} //for 9, subtract from power of 10

        string roman = romanIntMap[subtrahend] + romanIntMap[minuend];
        return roman;
    }


    string handleAdditiveCase(int digit, int weight, unordered_map<int, string> romanIntMap) {
        int baseAddend;
        int iterations; //the number of times you have to add powers of 10 to the base addend
        if (digit > 5) {
            baseAddend = this->getClosestNumStartingWith5(weight);
            iterations = digit - 5;
        }
        else {
            baseAddend = this->getClosestPowerOf10(digit, weight);
            iterations = digit - 1;
        }

        string roman = romanIntMap[baseAddend];

        int pow10Addend = pow(10,weight);
        for (int i = 0; i < iterations; i++) {
            roman += romanIntMap[pow10Addend];
        }
        return roman;
    }


    //closest number starting with 5 will have the same place value weight as the number
    //e.g. 40 and 50 both have the same weight 1 (10^1)
    //so closestNumStartingWith5(num, weight) = 5 * 10^weight
    int getClosestNumStartingWith5(int weight) {
        return 5 * pow(10,weight);
    }

    //closest power of 10 will have
    //  same place value weight if number is < 5 (e.g. 10 and 20, 30, 40)
    //  one place value weight higher if number is > 5 (e.g 100 and 60, 70, 80)
    int getClosestPowerOf10(int digit, int weight) {
        if (digit < 5) {return pow(10,weight);}
        return pow(10,weight + 1);
    }

    //pairwise mapping of the integers to their roman characters sorted in descending order
    unordered_map<int, string> createRomanIntMap() {
        unordered_map<int, string> romanIntMap = {
            {1000, "M"}, {500, "D"}, {100, "C"},
            {50, "L"}, {10, "X"}, {5, "V"}, {1, "I"},
        };
        return romanIntMap;
    }
};

class IntegerToRomanSolution2_12 {
    unordered_map<int, string> romanIntMap;
    /*
    Idea:
    -> remove all the pow() operations.
    -> The pow() operations have to be done because we are tracking the exponents of 10 (like 2 for 10^2)
       and then performing the exponentiation operation.
    -> We could avoid this redundancy and just directly track the power of 10 value (like 100, 1000, etc).
    -> Instead of increasing the exponent value by 1 every iteration, we multiply it by 10

    -> Make romanIntMap a class attribute since all methods are using it. Create it in the constructor.
    */
    public:
    IntegerToRomanSolution2_12() {
        romanIntMap = this->createRomanIntMap();
    }
    string intToRoman(int num) {
        string roman = "";
        int weight = 1; //store weight as the power of 10 and not just the exponent (e.g. 100 instead of 2 for 10^2)

        while (num > 0) {
            int digit = num % 10;

            if (digit == 1 || digit == 5) {
                roman = romanIntMap[digit * weight] + roman;
            }
            else if (digit == 4 || digit == 9) {
                roman = this->handleSubtractiveCase(digit, weight) + roman;
            }
            else if (digit != 0) {
                roman = this->handleAdditiveCase(digit, weight) + roman;
            }

            weight *= 10; //Weight of the next digit is multiplied by 10
            num /= 10;
        }
        return roman;
    }

    //Cases for constructing the roman
    private:
    string handleSubtractiveCase(int digit, int weight) {
        int minuend;
        //REMOVED SUBTRAHEND: the number that you are subtracting is the power of 10, which is now the weight

        if (digit == 4) {minuend = this->getClosestNumStartingWith5(weight);}
        else {minuend = this->getClosestPowerOf10(digit, weight);}

        string roman = romanIntMap[weight] + romanIntMap[minuend];
        return roman;
    }


    string handleAdditiveCase(int digit, int weight) {
        int baseAddend;
        int iterations; //the number of times you have to add powers of 10 to the base addend
        if (digit > 5) {
            baseAddend = this->getClosestNumStartingWith5(weight);
            iterations = digit - 5;
        }
        else {
            baseAddend = this->getClosestPowerOf10(digit, weight);
            iterations = digit - 1;
        }

        string roman = romanIntMap[baseAddend];

        //pow10Addend will be the weight
        for (int i = 0; i < iterations; i++) {
            roman += romanIntMap[weight];
        }
        return roman;
    }


    //closest number starting with 5 will have the same place value weight as the number
    //e.g. 40 and 50 both have the same weight 1 (10^1)
    //so closestNumStartingWith5(num, weight) = 5 * 10^weight
    int getClosestNumStartingWith5(int weight) {
        return 5 * weight;
    }

    //closest power of 10 will have
    //  same place value weight if number is < 5 (e.g. 10 and 20, 30, 40)
    //  one place value weight higher if number is > 5 (e.g 100 and 60, 70, 80)
    int getClosestPowerOf10(int digit, int weight) {
        if (digit < 5) {return weight;}
        return weight * 10;
    }

    //pairwise mapping of the integers to their roman characters sorted in descending order
    unordered_map<int, string> createRomanIntMap() {
        unordered_map<int, string> romanIntMap = {
            {1000, "M"}, {500, "D"}, {100, "C"},
            {50, "L"}, {10, "X"}, {5, "V"}, {1, "I"},
        };
        return romanIntMap;
    }
};