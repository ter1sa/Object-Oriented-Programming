#ifndef ANIMAL_H
#define ANIMAL_H

using namespace std ;
enum COLOR { Green=1, Blue, White, Black, Brown } ;

class Animal {
public :
    Animal() : _name("unknown") {
        //cout << "constructing Animal object "<< _name << endl ;
    }

    Animal(string n, COLOR c) : _name(n), _color(c){
        //cout << "constructing Animal object "<< _name << " with color scheme "<< _color<< endl ;
    }
    virtual void speak() const {
        cout << "Animal speaks "<< endl ;
    }
    virtual void move() const = 0;

    string getName() const{
        return _name;
    }
    int getColor() const{
        return _color;
    }
    ~Animal() {
        //cout << "destructing Animal object "<< _name << endl ;
    }



private :
    string _name;
    COLOR _color ;
};

class Mammal: public Animal{
public:
    Mammal() : Animal(){//cout << "constructing Mammal object "<< Animal::getName() << endl;
    }
    Mammal(string n, COLOR c) : Animal(n, c){
        //cout << "constructing Mammal object "<< Animal::getName() << " with color scheme "<< Animal::getColor()<< endl ;
    }

    void eat() const{
        cout<< "Mammal eats" << endl;
    }
    void move() const{
        cout << "walk"<< endl;
    }

    ~Mammal(){
    }


};
#endif // ANIMAL_H
