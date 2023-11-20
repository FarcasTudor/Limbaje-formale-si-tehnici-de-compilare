#include <iostream>

int main()
{
    int a, b;
    std::cout<<"a= ";
    std::cin>>a;
    std::cout<<"b= ";
    std::cin >> b;
    while (a != b) {
        if (a > b) a = a - b;
        else b = b - a;
    }
    std::cout<<"cmmdc= "<<a;
}
