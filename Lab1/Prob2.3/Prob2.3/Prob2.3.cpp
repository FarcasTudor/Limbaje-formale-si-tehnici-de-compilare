#include <iostream>

int main()
{
    int n, i, nr, suma;
    suma = 0;
    std::cout << "n = ";
    std::cin >> n;
    std::cout << "introduceti numerele:\n";
    for (i = 0; i < n; i=i+1) {
        std::cin >> nr;
        suma = suma + nr;
    }
    std::cout << "suma = " << suma;
}
