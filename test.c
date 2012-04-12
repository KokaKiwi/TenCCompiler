void main()
{
    write('H');
    write('e');
    write('l');
    write('l');
    write('o');
}

void write(char c)
{
    int pointer = 0x8000 + [0x0100];
    
    [pointer] = c;
    
    [0x0100]++;
}