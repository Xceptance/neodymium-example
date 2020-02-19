package posters.dataobjects;

public class Addresses
{
    private Address address;

    private Address newAddress;

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Address getNewAddress()
    {
        return newAddress;
    }

    public void setNewAddress(Address newAddress)
    {
        this.newAddress = newAddress;
    }

    @Override
    public String toString()
    {
        return "Addresses [address=" + address + ", newAddress=" + newAddress + "]";
    }

}
