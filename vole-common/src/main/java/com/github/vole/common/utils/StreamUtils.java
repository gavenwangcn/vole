package com.github.vole.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 流关闭
 */
public class StreamUtils {

	public static void close(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(InputStream... inputStreams) {
		if (inputStreams != null && inputStreams.length > 0) {
			for (InputStream is : inputStreams) {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public static void close(InputStream inputStream, OutputStream... outputStreams) {
		try {
			if (inputStream != null)
				inputStream.close();
		} catch (IOException e) {
		}
		if (outputStreams != null && outputStreams.length > 0) {
			for (OutputStream os : outputStreams) {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public static void close(OutputStream outputStream) {
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(OutputStream... outputStreams) {
		if (outputStreams != null && outputStreams.length > 0) {
			for (OutputStream os : outputStreams) {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public static void close(OutputStream outputStream, InputStream... inputStreams) {
		if (inputStreams != null && inputStreams.length > 0) {
			for (InputStream is : inputStreams) {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
					}
				}
			}
		}
		try {
			if (outputStream != null)
				outputStream.close();
		} catch (IOException e) {
		}
	}

	public static void close(InputStream inputStream, OutputStream outputStream) {
		try {
			if (inputStream != null)
				inputStream.close();
			if (outputStream != null)
				outputStream.close();
		} catch (IOException e) {
		}
	}

	public static void close(AutoCloseable... closeables) {
		if (closeables != null && closeables.length > 0) {
			for (AutoCloseable ac : closeables) {
				if (ac != null) {
					try {
						ac.close();
					} catch (Exception e) {
					}
				}
			}
		}
	}
}
