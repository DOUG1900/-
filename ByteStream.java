public class ByteStream {
	byte bytes[];  //byte���飬��������
	int length;    //����
	ByteStream(){
		this.length = 0;
		this.bytes = null;
	}
	
	ByteStream(int len, int num){
		bytes = new byte[len];
		Int2Byte(this.bytes,num);
	}
	
	ByteStream(int len, String str){
		bytes = new byte[len];
		this.bytes = str.getBytes();
	}
	
	public byte[] ConnectBytes(byte[] bt1, byte[] bt2){
		byte[] result = new byte[bt1.length + bt2.length];
		System.arraycopy(bt1, 0, result, 0, bt1.length);  
        System.arraycopy(bt2, 0, result, bt1.length, bt2.length); 
		
        return result;
	}
	
	public void Split(byte[] bt, byte[] bt1, int p1, byte[] bt2, int p2, byte[] bt3, int p3, byte[] bt4){
		//���ղ�ͬ����Ϣ��ʽ�ָ���Ϣ���ݣ�btΪԭ���������ݣ�bt1-bt4����˳�򱣴�������ݣ�p1-p3�������ָ���λ��
		for(int i = p1-1; i >= 0; i--){
			bt1[i] = bt[i];
		}
		for(int i = p2-1; i >= p1; i--){
			bt2[i-p1] = bt[i];
		}
		for(int i = p3-1; i >= p2; i--){
			bt3[i-p2] = bt[i];
		}
		for(int i = bt.length-1; i >= p3; i--){
			bt4[i-p3] = bt[i];
		}
	}
	
	public void Int2Byte(byte[] bytes, int temp) { //��int��ת��Ϊbyte
        bytes[0] = (byte)(temp >>> 24) ;//bytes[0]��ʾһ��intֵ�����8λ
        bytes[1] = (byte)(temp >>> 16);//bytes[1]��ʾһ��intֵ�Ľ�������8λ
        bytes[2] = (byte)(temp >>> 8);//bytes[2]��ʾһ��intֵ���ٽ�������8λ
        bytes[3] = (byte)(temp);      //bytes[3]��ʾһ��intֵ�����8λ
    }
	public int Byte2Int(byte[] bytes) {  // ��byteת��Ϊint
        return (bytes[0] << 24)           | //��ԭintֵ���8λ
                ((bytes[1] & 0xff) << 16) | //��ԭintֵ��������8λ
                ((bytes[2] & 0xff) << 8 ) |//��ԭintֵ�ٽ�������8λ
                (bytes[3] & 0xff);         //��ԭintֵ�����8λ
    }

}
