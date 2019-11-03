package org.ygq.tools.design.state2;

public abstract class State {
	// 定义一个环境角色 提供子类访问
	protected OrderContext mOrderContext;

	public State setmOrderContext(OrderContext mOrderContext) {
		this.mOrderContext = mOrderContext;
		return this;
	}

	public abstract Builder getBuilder();

	public static class Builder {
		private StateVisibility stateVisibility;
		private boolean evaluateVisible;
		private boolean askAgainVisible;
		private boolean reConsultVisible;
		private boolean deleteOrderVisible;
		private boolean modifyMsgVisible;
		private boolean reminderVisible;

		public Builder() {
			stateVisibility = new StateVisibility();
		}

		public Builder setEvaluateVisible(boolean evaluateVisible) {
			this.evaluateVisible = evaluateVisible;
			return this;
		}

		public Builder setAskAgainVisible(boolean askAgainVisible) {
			this.askAgainVisible = askAgainVisible;
			return this;
		}

		public Builder setReConsultVisible(boolean reConsultVisible) {
			this.reConsultVisible = reConsultVisible;
			return this;
		}

		public Builder setDeleteOrderVisible(boolean deleteOrderVisible) {
			this.deleteOrderVisible = deleteOrderVisible;
			return this;
		}

		public Builder setModifyMsgVisible(boolean modifyMsgVisible) {
			this.modifyMsgVisible = modifyMsgVisible;
			return this;
		}

		public Builder setReminderVisible(boolean reminderVisible) {
			this.reminderVisible = reminderVisible;
			return this;
		}

		private void build() {
			stateVisibility.reminderVisible = reminderVisible;
			stateVisibility.modifyMsgVisible = modifyMsgVisible;
			stateVisibility.deleteOrderVisible = deleteOrderVisible;
			stateVisibility.evaluateVisible = evaluateVisible;
			stateVisibility.askAgainVisible = askAgainVisible;
			stateVisibility.reConsultVisible = reConsultVisible;
		}

		private class StateVisibility {
			boolean evaluateVisible;
			boolean askAgainVisible;
			boolean reConsultVisible;
			boolean deleteOrderVisible;
			boolean modifyMsgVisible;
			boolean reminderVisible;
		}
	}
}