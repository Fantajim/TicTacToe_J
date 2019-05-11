
public class SingleController extends Controller<SingleModel, SingleView> {

    ServiceLocator serviceLocator;

    public SingleController(SingleModel model, SingleView view) {
        super(model, view);

        serviceLocator = ServiceLocator.getServiceLocator();
        serviceLocator.getLogger().info("Single controller initialized");
    }

}