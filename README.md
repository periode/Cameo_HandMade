# Cameo_HandMade
Processing code for live visuals at Cameo Gallery on 15/10/29


All the visuals are pre-programmed into classes, and then triggered using midi notes (in noteOn()), or CC values (in controllerChange()), along with some helper functions mapped to keyboard.

Elements:

- Particles are the two little squares moving along cosine curves
- Links are either orthogonal or digonal lines stretching from point in the grid to the other
- Comets are the octogonal shapes, coming in two sizes (small and large)
