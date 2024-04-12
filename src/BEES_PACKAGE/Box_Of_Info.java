package BEES_PACKAGE;

public  class Box_Of_Info{
    public int x_position_of_source;
    public int y_position_of_source;
    public int quality_of_source ;
    Box_Of_Info(int quality_of_source)
    {
        this.quality_of_source = quality_of_source;
    }
    public Box_Of_Info(int x_position_of_source,int y_position_of_source)
    {
        this.x_position_of_source = x_position_of_source;
        this.y_position_of_source = y_position_of_source;
    }
}
