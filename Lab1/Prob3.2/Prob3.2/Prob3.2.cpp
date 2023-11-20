#include <iostream>

int main()
{
    int raza_cerc = 10; // nu exista "_"
    float ariaCercului = 3.14159265359 * raza_cerc * raza_cerc; // lungimea variabilei depaseste 8 caractere
    std::cout << "Aria = " << ariaCercului;
}
