public class DropDownChoiceForString<T> extends DropDownChoice<T> {

    private IModel<String> targetModel;

    public DropDownChoiceForString(String id, IModel<T> model, IModel<String> targetModel, 
            List<? extends T> choices,
            IChoiceRenderer<? super T> renderer) {
        super(id, model, choices, renderer);
        this.targetModel = targetModel;
    }

    protected DropDownChoiceForString(String id, IModel<T> model, IModel<String> targetModel) {
        this(id, model, targetModel, Collections.<T> emptyList(), null);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // load the initial choice.
        setModelObject(convertChoiceIdToChoice(targetModel.getObject()));
    }

    @Override
    protected void onDetach() {
        super.onDetach();

        targetModel.detach();
    }

    @Override
    protected void onModelChanged() {
        super.onModelChanged();

        T newSelection = getModelObject();

        int choiceIndex = getChoices().indexOf(newSelection);
        // update the string source with the selected value.
        targetModel.setObject(getChoiceRenderer().getIdValue(newSelection, choiceIndex));
    }
}
