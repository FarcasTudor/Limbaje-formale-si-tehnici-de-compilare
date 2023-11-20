#include <iostream>

int main()
{
    int raza;
    float perimetru, arie;
    std::cout<<"Introduceti raza cercului";
    std::cin>>raza;
    perimetru=2 * 3.14159265359 * raza;
    arie=3.14159265359 * raza * raza;
    std::cout << "Perimetru: " << perimetru << "\nAria: " << arie;
}
