#include "animal.h"

#ifndef CHILDANIMAL_H
#define CHILDANIMAL_H

using namespace std ;

class Dog: public Mammal{
public:
    Dog() : Mammal(){cout << "Dog "<< Animal::getName() <<"selected "<<  endl;}
    Dog(string n, COLOR c, string owner): Mammal(n, c), _owner(owner){
        cout << "Dog "<< Animal::getName() << " with color scheme "<< Animal::getColor()
        << " and owner "<< _owner<< " selected"<< endl ;
    }
    void speak()const{
        cout<<"Woof Woof" <<endl;
    }
    void move() const{
        cout<<"Dog walks"<<endl;
    }
    ~Dog() {
    }
private:
    string _owner;
};

class Cat: public Mammal{
public:
    Cat() : Mammal(){cout << "Cat "<< Animal::getName() <<"selected "<<  endl;}
    Cat(string n, COLOR c, string owner): Mammal(n, c), _owner(owner){
        cout << "Cat "<< Animal::getName() << " with color scheme "<< Animal::getColor()
        << " and owner "<< _owner<< " selected"<< endl ;
    }
    void speak()const{
        cout<<"Meow Meow" <<endl;
    }
    void move() const{
        cout<<"Cat walks"<<endl;
    }
    ~Cat() {
    }
private:
    string _owner;
};

class Lion: public Mammal{
public:
    Lion(): Mammal(){cout << "Lion "<< Animal::getName() <<"selected "<<  endl;}
    Lion(string n, COLOR c, string owner): Mammal(n, c), _owner(owner){
        cout << "Lion "<< Animal::getName() << " with color scheme "<< Animal::getColor()
        << " and owner "<< _owner<< " selected"<< endl ;
    }
    void speak()const{
        cout<<"Roar" <<endl;
    }
    void move() const{
        cout<<"Lion walks"<<endl;
    }
    ~Lion() {
    }
private:
    string _owner;
};

#endif // CHILDANIMAL_H
