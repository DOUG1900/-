public class RespMsg extends Message{
	OctetString status;
	OctetString description;
	RespMsg(int ID, int len, String stat, String dsp) {
		super();
		this.commandID = ID;
		this.totalLength = len;
		this.status = new OctetString(1, stat);
		this.description = new OctetString(64, dsp);
	}
	RespMsg() {
		super();
		this.commandID = 0;
		this.totalLength = 73;
		this.status = null;
		this.description = null;
	}
	
	public String getStatus(){
		return this.status.getValue();
	}
	
	public String getDescription(){
		return this.description.getValue();
	}
	
	public byte[] getAll(){
		byte[] btstream = new byte[this.totalLength];
		byte[] bt1 = new byte[4]; //��Ϣͷ��һ���֣��ܳ���
		byte[] bt2 = new byte[4]; //��Ϣͷ�ڶ����֣�ID
		byte[] bt3 = new byte[1]; //status
		byte[] bt4 = new byte[64]; //description
		
		ByteStream  bs = new ByteStream();  //����������Ϣת��Ϊ�ֽ�����
		bs.Int2Byte(bt1, this.totalLength);
		bs.Int2Byte(bt2, this.commandID);
		bt3 = this.getStatus().getBytes();
		bt4 = this.getDescription().getBytes();
		
		btstream = bs.ConnectBytes(bt1, bt2);  //�ϲ���һ���ֽ�����
		btstream = bs.ConnectBytes(btstream, bt3);
		btstream = bs.ConnectBytes(btstream, bt4);
		
		return btstream;
	}
}