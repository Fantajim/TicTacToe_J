public class SingleModel extends Model{

  ServiceLocator serviceLocator;

  public SingleModel(){

      serviceLocator = ServiceLocator.getServiceLocator();
      serviceLocator.getLogger().info("Single model initialized");

  }

}
