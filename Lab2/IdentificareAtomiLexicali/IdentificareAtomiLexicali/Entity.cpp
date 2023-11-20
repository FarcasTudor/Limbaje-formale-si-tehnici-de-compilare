#include <string>
#include <iostream>
#include <fstream>

using namespace std;

class Entity {
	string simbol;
	int cod;

	public:
		Entity(string simbol, int cod) {
			this->simbol = simbol;
			this->cod = cod;
		}


	string getSimbol() {
		return this->simbol;
	}

	int getCod() {
		return this->cod;
	}

	void setSimbol(string simbol) {
		this->simbol = simbol;
	}

	void setCod(int cod) {
		this->cod = cod;
	}

	void print(ostream& output) {
		if (cod == -1) {
			output<< cod << " -> Eroare lexicala\n";
		}
		else {
			output << cod << '\n';
		}
	}
};