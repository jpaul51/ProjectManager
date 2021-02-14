package com.jonas.suivi.views.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridNoneSelectionModel;
import com.vaadin.flow.component.grid.GridSelectionModel;

public class GridCustom<T> extends Grid<T> {
	

	public enum SelectionMode  {

        /**
         * Single selection mode that maps to built-in {@link Single}.
         *
         * @see GridSingleSelectionModel
         */
//        SINGLE {
//           
//            protected <T> GridSelectionModel<T> createModel(Grid<T> grid) {
//                return new AbstractGridSingleSelectionModel<T>(grid) {
//
//                    @SuppressWarnings("unchecked")
//                    @Override
//                    protected void fireSelectionEvent(
//                            SelectionEvent<Grid<T>, T> event) {
//                        super.getGrid().fireEvent((ComponentEvent<Grid<T>>) event);
//                    }
//
//                    @Override
//                    public void setDeselectAllowed(boolean deselectAllowed) {
//                        super.setDeselectAllowed(deselectAllowed);
//                        grid.getElement().executeJs(
//                                "this.$connector.deselectAllowed = $0",
//                                deselectAllowed);
//                    }
//                };
//            }
//        },

        /**
         * Multiselection mode that maps to built-in
         * {@link SelectionModel.Multi}.
         *
         * @see GridMultiSelectionModel
//         */
//        MULTI {
//            
//            protected <T> GridSelectionModel<T> createModel(Grid<T> grid) {
//                return new AbstractGridMultiSelectionModel<T>(grid) {
//
//                    @SuppressWarnings("unchecked")
//                    @Override
//                    protected void fireSelectionEvent(
//                            SelectionEvent<Grid<T>, T> event) {
//                        grid.fireEvent((ComponentEvent<Grid<?>>) event);
//                    }
//                };
//            }
//        },

        /**
         * Selection model that doesn't allow selection.
         *
         * @see GridNoneSelectionModel
         */
        NONE {
            
            protected <T> GridSelectionModel<T> createModel(Grid<T> grid) {
                return new GridNoneSelectionModel<>();
            }
        };
	}

}
