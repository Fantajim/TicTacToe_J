

public class SingleController extends Controller<SingleModel, SingleView> {

    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);

        for (int i =0;i<3;i++){
            for(int j=0;j<3;j++){
                view.getCell(i,j).setOnMouseClicked(event -> {
                   int index[] = getButtonPressed((Cell)event.getSource());
                   view.getCell(index[0],index[1]).setIdent(model.getCurrentPlayer());
                   model.toggleCurrentPlayer();

                });

            }
               }
        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single controller initialized");
    }

    public void buttonClick(){

    }

    public int[] getButtonPressed(Cell c){
        int index[] = new int [2];
        for (int i = 0;i<3;i++){
            for (int j = 0;j<3;j++){
                if (c == view.getCell(i,j)){
                    index[0] = i;
                    index[1] = j;
                }
            }

        }
        return index;
    }

}