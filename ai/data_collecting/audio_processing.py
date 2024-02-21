import os
import librosa
import soundfile as sf
import numpy as np

def preprocess_audio(input_path, output_path):
    # Create the output directory if it does not exist
    if not os.path.exists(output_path):
        os.makedirs(output_path)

    # Iterate over all WAV files in the input directory
    for filename in os.listdir(input_path):
        if filename.endswith(".wav"):
            # Load the WAV file
            file_path = os.path.join(input_path, filename)
            y, sr = librosa.load(file_path, sr=None)

            # Perform noise removal and other preprocessing tasks
            # Add your desired preprocessing code here
            # Denoise the audio
            D = librosa.amplitude_to_db(np.abs(librosa.stft(y)), ref=np.max)
            D_denoised = librosa.decompose.nn_filter(D, aggregate=np.median)
            y_denoised = librosa.griffinlim(librosa.db_to_amplitude(D_denoised))

            # Remove noise
            y_denoised = librosa.effects.preemphasis(y)
            y_denoised, index = librosa.effects.trim(y_denoised)

            # Save the preprocessed audio to a new file
            output_file_path = os.path.join(output_path, filename)
            sf.write(output_file_path, y_denoised, sr)
            print(f"Preprocessed {filename} and saved to {output_file_path}")

# Set the directories and paths for the example
input_directory = "/content/extract"
output_directory = "/content/processed"

# Call the preprocessing function
preprocess_audio(input_directory, output_directory)
