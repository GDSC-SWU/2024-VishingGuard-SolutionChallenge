def convert_to_16bit_wav(input_path, output_path):
    with wave.open(input_path, 'rb') as wav_in:
        with wave.open(output_path, 'wb') as wav_out:
            wav_out.setnchannels(wav_in.getnchannels())
            wav_out.setsampwidth(2)  # setting 16bit
            wav_out.setframerate(wav_in.getframerate())
            wav_out.writeframes(wav_in.readframes(wav_in.getnframes()))
