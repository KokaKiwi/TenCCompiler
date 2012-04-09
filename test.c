void main()
{
    int a;
    
    a = 0;
    [0x0100] = 'A';
    
    while(a < 30)
    {
        [0x8000 + a] = [0x0100] + a;
        
        a++;
    }
}