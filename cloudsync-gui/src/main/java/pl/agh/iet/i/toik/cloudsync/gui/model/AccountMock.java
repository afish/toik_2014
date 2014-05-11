package pl.agh.iet.i.toik.cloudsync.gui.model;

public class AccountMock {

	private String accountName;
	private CloudTypeMock cloudType;
	public AccountMock(String accountName, CloudTypeMock cloudType) {
		super();
		this.accountName = accountName;
		this.cloudType = cloudType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public CloudTypeMock getCloudType() {
		return cloudType;
	}
	public void setCloudType(CloudTypeMock cloudType) {
		this.cloudType = cloudType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result
				+ ((cloudType == null) ? 0 : cloudType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountMock other = (AccountMock) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (cloudType != other.cloudType)
			return false;
		return true;
	}
	
	
	
}
