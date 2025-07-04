package com.revoktek.motivus.core.constants.request;

public class PedidoPath {

    public static final String CONTROLLER = "/pedido";
    public static final String FIND_ALL_BY_FILTER = "/findAllByFilter";
    public static final String FIND_ALL_BY_FILTER_PAGED = "/findAllByFilterPaged";
    public static final String FIND_BY_ID = "/findById";
    public static final String FIND_ALL_BY_EMPLEADO_ENTREGA = "/findAllByEmpleadoEntrega";
    public static final String FIND_ALL_PRODUCTS_BY_EMPLEADO_ENTREGA = "/findAllProductsByEmpleadoEntrega";
    public static final String FIND_BY_CLIENT = "/findByClient";
    public static final String SAVE = "/save";
    public static final String SAVE_DISPATCH = "/saveDispatch";
    public static final String SAVE_PEDIDO_EXTEMPORANEO = "/saveExtemporaneo";
    public static final String SAVE_EMPLEADO_ENTREGA = "/saveEmpleadoEntrega";
    public static final String REMOVE_EMPLEADO_ENTREGA = "/removeEmpleadoEntrega";
    public static final String SAVE_DELIVERY = "/saveDelivery";
    public static final String PEDIDO_PRODUCTO_CANCEL = "/pedidoProductoCancel";

    private PedidoPath() {
    }

}
