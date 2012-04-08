void main()
{
    int pointer = 0x8000;
    char c = 'A';
    int counter = 0;
    
    while(counter < 26)
    {
        [pointer] = c;
        
        c = c + 1;
        pointer = pointer + 1;
        counter = counter + 1;
    }
}