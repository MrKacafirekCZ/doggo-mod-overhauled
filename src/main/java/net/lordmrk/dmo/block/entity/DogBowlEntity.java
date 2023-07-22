package net.lordmrk.dmo.block.entity;

import net.lordmrk.dmo.DoggoModOverhauled;
import net.lordmrk.dmo.inventory.ImplementedInventory;
import net.lordmrk.dmo.screen.DogBowlScreenHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;

public class DogBowlEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory,  ScreenHandlerListener, Nameable {

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
	private Text customName;

	public DogBowlEntity(BlockPos pos, BlockState state) {
		super(DoggoModOverhauled.DOG_BOWL_ENTITY, pos, state);
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		DogBowlScreenHandler handler = new DogBowlScreenHandler(syncId, inv, this);
		handler.addListener(this);
		return handler;
	}

	public Text getCustomName() {
		return this.customName;
	}

	@Override
	public Text getDisplayName() {
		return this.getName();
	}

	public int getFoodHunger() {
		if(!items.get(0).isEmpty() && items.get(0).getItem().isFood()) {
			return items.get(0).getItem().getFoodComponent().getHunger() * 4;
		}

		return 0;
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	public Text getName() {
		return this.customName != null ?
				Text.translatable(
						"block.doggomodoverhauled.named_dog_bowl_entity",
						this.customName.getString()) :
				Text.translatable("block.doggomodoverhauled.dog_bowl_entity");
	}

	public void foodEaten() {
		if(items.get(0) != null && !items.get(0).isEmpty()) {
			items.get(0).decrement(1);
			markDirty();
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		items.clear();
		Inventories.readNbt(nbt, items);
		if(nbt.contains("CustomName", 8)) {
			this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
		}
		markDirty();
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		nbt = Inventories.writeNbt(nbt, items);
		if(this.customName != null) {
			nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
		}
	}

	public boolean hasCustomName() {
		return this.customName != null;
	}

	public boolean hasFood() {
		return items.get(0) != null && !items.get(0).isEmpty();
	}

	public void setCustomName(Text customName) {
		this.customName = customName;
	}

//	public void fromClientTag(NbtCompound nbt) {
//		DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
//
//		Inventories.readNbt(nbt, items);
//
//		for(int i = 0; i < this.items.size(); i++) {
//			this.items.set(i, items.get(i));
//		}
//
//		if(nbt.contains("CustomName", 8)) {
//			this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
//		}
//	}

	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Override
	public void markDirty() {
		if (this.world != null) {
			markDirtyInWorld(this.world, this.pos, this.getCachedState());
		}
	}

	protected void markDirtyInWorld(World world, BlockPos pos, BlockState state) {
		world.markDirty(pos);
		if (!world.isClient()) {
			((ServerWorld) world).getChunkManager().markForUpdate(pos); // Mark changes to be synced to the client.
		}
	}


	@Override
	public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {

	}

	@Override
	public void onPropertyUpdate(ScreenHandler handler, int property, int value) {

	}
}
