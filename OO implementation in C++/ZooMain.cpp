#include <iostream>
#include <string>
#include "animal.h"
#include "childAnimal.h"

using namespace std ;

int main() {

    int choice, number, index = 0;
    int i=1, j=1, k=1;
    int a;

    cout<<"Enter the number of animals to move: ";
    scanf("%d", &number);

    Mammal **mammal = new Mammal*[number];
    while(true){
        cout<<"Select the animal to send to Zoo: \n";
        cout<<"(1)Dog (2)Cat (3)Lion (4)Move all animals (5)Quit" << endl;
        cout<<"Enter your choice: ";
        scanf("%d", &choice);
        switch(choice){
        case 1:
            if(index < number){
                mammal[index] = new Dog("dog"+to_string(i), Black, "owner"+to_string(i));
                i++, index++;
            }
            else cout<<"Maximum number of animals selected. "<< endl;
            break;
        case 2:
            if(index < number){
                mammal[index] = new Cat("cat"+to_string(j), Black, "owner"+to_string(j));
                j++, index++;
            }
            else cout<<"Maximum number of animals selected. "<< endl;
            break;
        case 3:
           if(index < number){
                mammal[index] = new Lion("lion"+to_string(k), Black, "owner"+to_string(k));
                k++, index++;
            }
            else cout<<"Maximum number of animals selected. "<< endl;
            break;
        case 4:
            for(a=0; a<index; a++){
                mammal[a]->move();
                mammal[a]->speak();
                mammal[a]->eat();
            }
            break;
        case 5:
            cout << "Program exiting ... "<< endl ;
            break;
        default:
            cout<<"Incorrect input, please reenter your choice";
        }
        if(choice == 5)break;
    }

    return 0;
}
