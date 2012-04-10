void main()
{
    char c;
    int pointer;
    int counter;
    
    c = 'A';
    counter = 0;
    
    while(counter < 64)
    {
        pointer = 0x8000 + counter;
        
        [pointer] = c;
        
        c++;
        counter++;
    }
}