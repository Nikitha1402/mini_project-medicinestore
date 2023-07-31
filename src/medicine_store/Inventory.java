
package medicine_store;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Medicine> medicines;

    public Inventory() {
        medicines = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
    }

    public Medicine findMedicineByName(String name) {
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;
    }

    public void updateStock(String name, int quantity) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            int newStock = medicine.getStock() + quantity;
            medicine.setStock(newStock);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("--------- Inventory ---------\n");
        for (Medicine medicine : medicines) {
            sb.append(medicine.toString()).append("\n");
        }
        sb.append("---------------------------");
        return sb.toString();
    }
}

